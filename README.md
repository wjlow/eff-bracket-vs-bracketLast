# Demo of Eff's bracket vs bracketLast behaviour

I suspect that `bracket` is not releasing under a fairly specific circumstance, demonstrated in Test 1 below.

Test 2 works fine.

The difference between the two tests is that the first test has a `for/yield` in `Delegator` whereas the second doesn't.

Using `bracket` in `LockHandler` passes #2 but not #1.
Using `bracketLast` in `LockHandler` passes both tests.

# How to run

```
$ ./sbt clean test
```

## Test 1

Spec -> Delegator.delegateWithFlatMap -> LockHandler (uses `bracket`)

This test times out, the Futures in the test never complete.

Changing `LockHandler` to use `bracketLast` fixes the problem of the test not completing.

## Test 2

Spec -> Delegator.delegate -> LockHandler (uses `bracket`)

This test does not time out. I suspect not having a `for/yield` in `Delegator.delegate` makes this different.
