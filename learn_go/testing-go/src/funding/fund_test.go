package funding

import (
	"sync"
	"testing"
	"fmt"
	"strconv"
)

const WORKERS = 10

func BenchmarkWithdrawals(b *testing.B) {
	// Skip N = 1
	if b.N < WORKERS {
		return
	}

	server := NewFundServer(b.N)


	// Casually assume b.N divides cleanly
	dollarsPerFounder := b.N / WORKERS

	fmt.Printf("b.N= %s   dollarsPerFounder=%s \n", strconv.Itoa(b.N), strconv.Itoa(dollarsPerFounder))

	// WaitGroup structs don't need to be initialized
	// (their "zero value" is ready to use).
	// So, we just declare one and then use it.
	var wg sync.WaitGroup

	for i := 0; i < WORKERS; i++ {
		// Let the waitgroup know we're adding a goroutine
		wg.Add(1)

		// Spawn off a founder worker, as a closure
		go func() {
			// Mark this worker done when the function finishes
			defer wg.Done()

			for i := 0; i < dollarsPerFounder; i++ {
				server.Commands <- WithdrawCommand{Amount: 1 }
			}

		}() // Remember to call the closure!
	}

	// Wait for all the workers to finish
	fmt.Println("Waiting for wokers")
	wg.Wait()

	balanceResponseChan := make(chan int)
	server.Commands <- BalanceCommand{Response: balanceResponseChan }

	balance := <-balanceResponseChan


	if balance != 0 {
		b.Error("Balance wasn't zero:", balance)
	}


}
