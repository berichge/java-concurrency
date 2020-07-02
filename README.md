# Java Concurrency

Why Concurrency: CPU, Memory and I/O have different performance. 

- cache layer was added to cpu
- operating system introduced process and thread. Utilize CPU by divided time
- the compiler re-order command to optimize

## Three key problems

- Visibility: caused by CPU caching
- Atomic: one or more command be executed in CPU without interrupt
- Ordering: sometime, compiler will reorder execution jobs to optimize system performance. Unexpected issues can be caused by this.

## Java memory model

### 3 key words

volatile: disable cpu cache
Volatile can solve visiblity problem, but it can cause synchronize problem since threads can update it simultaneously.

synchronize, final

6 happen-before rules:
The previous operation is visiable to the next operation

- 程序顺序执行
- volatile写对后续读可见
- 传递性 a->b b->c = a->c
- synchronized
- 子线程可见主线程调用start()前的任何操作
- 主线程通过join()看到子线程的操作

### 互斥

如果同一时刻只有一个线程执行，那么对共享变量的修改就是互斥的

create lock
lock()
critical section
unlock()

#### Lock in Java

Synchronized

Synchronized decoration can be used to lock

- Object
- Function
- Class

受保护资源和锁应该是 n:1 的关系

work with wait(), notify(), notifyAll()

仅当 所有等待线程执行相同操作，有相同的条件变量，并且只需要唤醒一个线程时，使用notify()

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

##### Fine grained lock

Improve performance by increasing concurrent computing and reducing locking of unrelated objects or functions

Avoid dead lock

Trigger condition of deadlock

- Mutual exclusion
- Hold and wait for paritial allocation: 用账本管理员管理
- no pre-emption: 如果不能运行，则主动释放资源
- circular wait: 用顺序申请破坏循环等待

### thread-safe

Make sure no other thread can access a shared data when a thread is modifying it. Guarantee the correctness of result by order of executation.

`Race Condition`: systems substantive behavoir is dependent on sequenece or timing of other uncontrollable events

### thread-availability

Deadlock: avoid logic that can cause deadlock by destroying deadlock conditions
Livelock: random sleep
Starvation: 1. enough resource 2. fair allocation 3. avoid long execution.

### Performance

Amdahl rule

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

### Monitor module

Manage shared data and operation on them.

Mutual exclusion and synchronization
管程封装共享资源，设置唯一入口和一个入口等待队列，只允许一个线程进入。条件变量有各自的等待队列（队列不空，队列不满都是等待条件）。

当条件不满足，则进入条件等待队列，当被通知满足后，将等待任务移入入口等待队列，并在此判断能否执行。

synchronized: wait(), notify(), notifyAll()

Invoking the notify() method is permitted only when all of the following conditions are met:
All waiting threads have identical condition predicates.
All threads perform the same set of operations after waking up. That is, any one thread can be selected to wake up and resume for a single invocation of notify().
Only one thread is required to wake upon the notification.

As stated in the definitions, CyclicBarrier allows a number of threads to wait on each other, whereas CountDownLatch allows one or more threads to wait for a number of tasks to complete.

## Java thread life cycle

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

#### Semaphore

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

伴随自旋: keep trying.

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

任务关系：串行，并行，汇聚

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

## Best Practices

永远只在更新对象的成员变量时加锁
永远只在访问可变的成员变量时加锁
永远不在调用其他对象的方法时加锁

[reactive]:(http://reactivex.io/intro.html)
[reentrantLockAndCondition]: (https://github.com/berichge/java-concurrency/tree/master/src/main/java/src/main/concurrency/lockAndCondition)