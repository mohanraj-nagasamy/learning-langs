package new_funding

import (
	"testing"
	"sync"
	"fmt"
	"runtime"
)

const WORKERS = 10

func BenchmarkFund(b *testing.B) {
	runtime.GOMAXPROCS(4)
	// Skip N = 1
	if b.N < WORKERS {
		return
	}
	// Add as many dollars as we have iterations this run
	server := NewFundServer(b.N)

	// Casually assume b.N divides cleanly
	dollarsPerFounder := b.N / WORKERS

	// WaitGroup structs don't need to be initialized
	// (their "zero value" is ready to use).
	// So, we just declare one and then use it.
	var wg sync.WaitGroup

	for i := 0; i < WORKERS; i++ {
		wg.Add(1)
		go func() {
			defer wg.Done()
			pizzaTime := false
			for i := 0; i < dollarsPerFounder; i++ {
				server.Transact(func(fund *Fund) {
					if fund.Balance() <= 10 {
						// Set it in the outside scope
						pizzaTime = true
						return
					}
					fund.Withdraw(1)
				})

				if pizzaTime {
					break
				}
			}
		}()
	}

	// Wait for all the workers to finish
	wg.Wait()

	balance := server.Balance()
	fmt.Println("fund.Balance : ", balance)
	if balance != 10 {
		b.Error("Balance wasn't ten dollars:", balance)
	}

}
