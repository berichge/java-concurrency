package concurrency.readWriteLock;

public class ReadAndWriteSample {

    public static void main(String[] args) throws InterruptedException {
        ReadAndWriteMocker mocker = new ReadAndWriteMocker();

        int readerCount = 20;
        int writerCount = 5;

        for (int i = 0 ; i < writerCount; i++) {
            new RwLockWriter(mocker).start();
        }

        for (int i = 0 ; i < readerCount; i++) {
            new RwLockReader(mocker).start();
        }
    }

}

class RwLockWriter extends Thread {
    private final ReadAndWriteMocker mocker;
    public RwLockWriter(ReadAndWriteMocker rwMocker) {
        mocker = rwMocker;
    }
    @Override
    public void run() {
        try {
            mocker.set();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class RwLockReader extends Thread {
    private final ReadAndWriteMocker mocker;
    public RwLockReader(ReadAndWriteMocker rwMocker) {
        mocker = rwMocker;
    }
    @Override
    public void run() {
        try {
            mocker.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}