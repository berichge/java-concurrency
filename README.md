
<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [Java Concurrency](#java-concurrency)
  - [What is concurrency](#what-is-concurrency)
  - [Three key problems in Java concurrency](#three-key-problems-in-java-concurrency)
    - [3 key words](#3-key-words)
      - [Visibility](#visibility)
      - [Atomic](#atomic)
      - [Ordering](#ordering)
    - [Happen before rules](#happen-before-rules)
  - [Java multi-threading basic](#java-multi-threading-basic)
    - [Synchronization](#synchronization)
      - [What is Synchronization](#what-is-synchronization)
      - [wait and notify](#wait-and-notify)
      - [Fine grained lock](#fine-grained-lock)
      - [Avoid deadlock](#avoid-deadlock)
        - [Deadlock conditions](#deadlock-conditions)
        - [Avoid deadlocks](#avoid-deadlocks)
      - [Thread-safe](#thread-safe)
      - [Java thread life cycle](#java-thread-life-cycle)
  - [Java tools for concurrency](#java-tools-for-concurrency)
    - [ReentrantrantLock and Condition](#reentrantrantlock-and-condition)
    - [Semaphore](#semaphore)
      - [ReadWriteLock](#readwritelock)
        - [StampedLock](#stampedlock)
        - [CountDownLatch](#countdownlatch)
        - [CyclicBarrier](#cyclicbarrier)
        - [Phaser](#phaser)
        - [Concurrent collections](#concurrent-collections)
        - [Atomic class](#atomic-class)
        - [Executor](#executor)
        - [Disruptor](#disruptor)
        - [CompletableFuture](#completablefuture)
        - [CompletionService](#completionservice)
        - [Fork/Join](#forkjoin)
    - [Performance](#performance)
      - [Other techs improves multi-thread performance](#other-techs-improves-multi-thread-performance)
        - [Performance indicator](#performance-indicator)
        - [Compare and swap](#compare-and-swap)
  - [Best Practices](#best-practices)

<!-- /code_chunk_output -->

# Java Concurrency

## What is concurrency

A modern computer has serval CPUs or cores, and job running in CPU normally need to coordinate with memory and I/O. While those component has different performance branchmark. Ordering those jobs and running other jobs while a job is waiting I/O response can dramastically improve overall performance of a system.

## Three key problems in Java concurrency

Visibility, atomic and ordering are main concept of correct concurrent programming.

### 3 key words

#### Visibility

To improve CPU computation performance, Operating system assigns local cache running close to CPU. Multiple CPU has their own local cache, which is invisible to other CPUs. Thus, when multiple jobs running in different CPUs access and modify shared data in the main memory. Visiblity issue will happen. Use ***volatile*** to force CPU read/write from main memory instead of it's local cache.

#### Atomic

An atomic action is one that effectively happened all at once without interruption. ***synchronized*** can be used to make sure expected commands were executed in order.

#### Ordering

Operating will re-order command for optimization purpose, also, commands in multithread cannot be executed with an expected order without in purpose synchronization design.

### Happen before rules

Here are basic rules of Java happen-before

- Single thread rule: each action in a single thread happens-before every action in that thread that comes later in the program order.
- Monitor lock rule: an unlock on a monitor lock happens-before every subsequent acquiring on the same monitor lock.
- Volatile variable rule: a write to volatile field happens-before every subsequent read of that same field.
- Thread start rule: a call to Thread.start() on a thread happens-before every action in the started thread.
- Thread join rule: All actions in a thread happens-before any other thread successfully return from a join on that thread.

## Java multi-threading basic

### Synchronization

#### What is Synchronization

Synchronization can be used to solve thread interference and memory consistency errors, but it can introduce thread contention issue.

Synchronized decoration can be used to lock `Object,Function,Class`, those locks are applied to different scope of data.

Keep in mind that the relationship between protected resource and lock is n-1 relationship.

#### wait and notify

Synchronized will excluded other thread obtain the monior, however, if current thread is waiting for execution of other thread, wait() can make the thread give up the monitor and go to sleep until other thread enters the same monitor and call notify().

    Thread1
    synchronzied() {
        if (...) {
            wait()
        }
    }

    Thread2
    synchronized() {
        if (...) {
            notify() or notifyAll()
        }
    }

#### Fine grained lock

Fine grained lock can reduce unnecessary locking, thus improve performance.

#### Avoid deadlock

The section refers from [Deadlocks](https://www.cs.uic.edu/~jbell/CourseNotes/OperatingSystems/7_Deadlocks.html)

##### Deadlock conditions

Here are four condition of deadlock, destroy any of those condition can destroy deadlock.

- ***Mutual Exclusion*** - At least one resource must be held in a non-sharable mode; If any other process requests this resource, then that process must wait for the resource to be released.  
Use lockness mode
- ***Hold and Wait*** - A process must be simultaneously holding at least one resource and waiting for at least one resource that is currently being held by some other process.  
Release 
- ***No preemption*** - Once a process is holding a resource ( i.e. once its request has been granted ), then that resource cannot be taken away from that process until the process voluntarily releases it.
- ***Circular Wait*** - A set of processes { P0, P1, P2, . . ., PN } must exist such that every P[ i ] is waiting for P[ ( i + 1 ) % ( N + 1 ) ]. ( Note that this condition implies the hold-and-wait condition, but it is easier to deal with the conditions if the four are considered separately. )

##### Avoid deadlocks

Deadlocks can be prevented by preventing at least one of the four required conditions:

- ***Mutual Exclusion***
  - Shared resources such as read-only files do not lead to deadlocks.
- ***Hold and Wait***
  - Require that all processes request all resources at one time. This can be wasteful of system resources if a process needs one resource early in its execution and doesn't need some other resource until much later.
- ***No Preemption***

- ***Circular Wait***
  - One way to avoid circular wait is to number all resources, and to require that processes request resources only in strictly increasing ( or decreasing ) order.

#### Thread-safe

Make sure no other thread can access a shared data when a thread is modifying it. Guarantee the correctness of result by order of executation.

`Race Condition`: systems substantive behavoir is dependent on sequenece or timing of other uncontrollable events

#### Java thread life cycle

NEW->RUNNABLE<->(BLOCKED, WAITING, TIMED_WAITING)
        |
     TERMINATED

Status change condition

Runnable -> Blocked (other thread is executing)
Runnable -> Waiting (wait(), join(), park())
New -> Runnable (run(), start())
Runnable -> Terminated (stop(), interrupt())

## Java tools for concurrency

### ReentrantrantLock and Condition

ReentrantrantLock means a lock can be lock() multiple times within calling thread. If the thread owns the lock, it will return immediately.

lock.lock() will lock resource.
condition.await() will release the occupation of current lock, and let current thread wait for the wake up signal() of the condition.
condition.signal() will revode the thread, and thus obtain the lock again.

[code sample][reentrantLockAndCondition]

### Semaphore

semaphore(int n): assign number of permits
semaphore.acquire(): take a permit
semaphore.release(): release a permit
use try{}finally{} to acquire and release permits

#### ReadWriteLock

Allow multiple reads operation to shared resource
Allow only one thread write to shared resource
During write, disable all read and other write operation

obtained readlock cannot be promoted to writelock, obtained writelock can be downgraded to readlock

##### StampedLock

Writing:
Reading: no read lock will be obtained when write lock is held
Optimistic Reading: no lock at all, be reading will failed when write lock is held

##### CountDownLatch

1 thread wait for multiple threads result.

##### CyclicBarrier

resource management across multiple threads

The key difference is that CountDownLatch separates threads into waiters and arrivers while all threads using a CyclicBarrier perform both roles.

With a latch, the waiters wait for the last arriving thread to arrive, but those arriving threads don't do any waiting themselves.
With a barrier, all threads arrive and then wait for the last to arrive.

##### Phaser



##### Concurrent collections

Synchronized collections
`List list = Collections. synchronizedList(new ArrayList());`

Concrruent collection: List,Set,Map,Queue(deque, block)

##### Atomic class

No lock solution: compare-and-swap(CAS). Supported by hardware.

Check expected value and current value in memory. 

ignore ABA problem

- Atomic primitive: AtomoicBoolean, AtomicInteger, AtomicLong
- Atomic reference: ABA problem. Use versionStamp
`final AtomicReference rf = new AtomicReference<>( new WMRange(0,0) );`
- Atomic array: AtomicIntegerArray, AtomicReferenceArray
- Atomic updater: can only apply to volatile data
- Atomic accumlator: faster

##### Executor

Thread creation is very expensive

Producer-Consumer Mode
In Java: ThreadPoolExecutor

corePoolSize
maximumPoolSize
keepAliveTime & unit
workQueue
threadFactory
handler

ExecutorService need to be shutdown once job finished, or the process will keep running.

##### Disruptor

##### CompletableFuture

Multithread -> Asynchronize

Runnable doesn't have return value
Supplier has return value

CompletionStage

- sequence relation: thenApply, thenAccept, thenRun, thenCompose
- and relation: thenCombine, thenAcceptBoth, runAfterBoth
- or relation: applyToEither, acceptEither, runAfterEither

[reactive][reactive]

##### CompletionService

- submit(): Callable<V> task
- take, poll - consume from blocking queue. used to retrieve response/computation result

***Executor*** is use no-boundary queue, which is not recommended

##### Fork/Join

Divide-Conquer

ForkJoinPool
ForkJoinTask: fork(): exec subtask aync, join(): block current thread and wait for subtask
 RecursiveAction and RecursiveTask

### Performance

#### Other techs improves multi-thread performance

1. Thread Local storage
2. Copy-on-write
3. Optimization lock
4. Java Atomic (Don't use lock at all.)
5. Disruptor

##### Performance indicator

- Throughput
- Latency
- Concurrency

##### Compare and swap

K-mers algorithm: (lock free approach)
Atomic classes internally use compare-and-swap instructions supported by modern CPUs to achieve synchronization. These instructions are generally much faster than locks.

    int compare_and_swap(location, old_val, new_val) {
        // read current val from location
        cur_val < read(location)
        if cur_val == old_val:
            set location = new_val
        return cur_val
    }

Your code will need to check if return value is new value. `compare_and_swap` is guarantee to be atomic by hardware.

## Best Practices

永远只在更新对象的成员变量时加锁
永远只在访问可变的成员变量时加锁
永远不在调用其他对象的方法时加锁

Copy-on-write: read more than write. use of Immutability pattern.
Local storage mode, avoid sharing storage: use ThreadLocal. Thread local.
Thread local, declare and release, thread local cannot be shared by different thread, thus it cannot be used in aync programming


[reactive]:(http://reactivex.io/intro.html)
[reentrantLockAndCondition]: (./src/main/java/)