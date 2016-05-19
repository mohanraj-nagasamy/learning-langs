
x <- rnorm(100)
d <- list(1, 2, 3)

hist(x)

v <- vector()

myfun <- function() {
  x <- rnorm(100)
  mean(x)
}

args(sd)


con <- url("http://en.wikipedia.org/wiki/Nate_Silver", "r")
x <- readLines(con)
head(x)
close(con)
args(lm)

add2 <- function(x, y) {
  x + y
}

above10 <- function(x) {
  use <- x > 10
  cat("use = ", use, class(use))
  x[use]
}

search()

x <- as.Date("1970-01-01")
unclass(x)
x <- as.Date("1970-01-02")
unclass(x)
x <- as.Date("1969-12-31")
unclass(x)

?strptime

# Loop Functions
# lapply, sapply, apply,

x <- list(a = 1:5, b = rnorm(10))
x <- list(1:5, rnorm(10))
lapply(x, mean)

lapply(1:4, runif)

?apply

# Debug
traceback()
debug(lm)
lm(zz ~ yy)
