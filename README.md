# Java Concurrency

Why Concurrency: CPU, Memory and I/O have different performance. To better utilize operating system resources, we need to introduce multiple job be executed by CPU divided by time.

To improve performance of system.

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

### Monitor

Manage shared data and operation on them.

Mutual exclusion and synchronization
管程封装共享资源，设置唯一入口和一个入口等待队列，只允许一个线程进入。条件变量有各自的等待队列（队列不空，队列不满都是等待条件）。

当条件不满足，则进入条件等待队列，当被通知满足后，将等待任务移入入口等待队列，并在此判断能否执行。

synchronized and wait(), notify(), notifyAll()



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

### Lock and condition

Destroy `no pre-emption` condition and avoid dead lock, based on Java synchronize, we need those additional features

1. 响应中断
2. 支持超时
3. 非阻塞的获取锁

try{}finally{}

release lock in finally block

### Locks

- ReentrantLock: ReentrantLock allow threads to enter into lock on a resource more than once
- Condition: 管程里的条件变量。不能用wait(), notify()
    - request 用 rpc 的 doReceived() 唤醒get()的等待功能
- 

## Best Practices

```
永远只在更新对象的成员变量时加锁
永远只在访问可变的成员变量时加锁
永远不在调用其他对象的方法时加锁
```