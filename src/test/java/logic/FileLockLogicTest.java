package logic;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.channels.OverlappingFileLockException;

import org.junit.jupiter.api.Test;

public class FileLockLogicTest {
	private static final String FILEPATH = "FileLockLogicTest.lock";

	@Test
	public void lockFileTest() {
		FileLockLogic lock = new FileLockLogic(FILEPATH);
		try {
			lock.lockFile();
			assertTrue(true);
		} finally {
			lock.unlockFile();
		}
	}

	@Test
	public void lockFileTest2() {
		FileLockLogic lock = new FileLockLogic(FILEPATH);
		try {
			lock.lockFile();
			try {
				lock.lockFile();
				fail("must throw IllegalStateException.");
			} catch (IllegalStateException e) {
				assertTrue(true);
			}
		} finally {
			lock.unlockFile();
		}
	}

	@Test
	public void lockFileTest3() {
		FileLockLogic lock1 = new FileLockLogic(FILEPATH);
		FileLockLogic lock2 = new FileLockLogic(FILEPATH);
		try {
			lock1.lockFile();
			try {
				lock2.lockFile();
				fail("must throw OverlappingFileLockException.");
			} catch (OverlappingFileLockException e) {
				assertTrue(true);
			}
		} finally {
			lock1.unlockFile();
		}
	}

	@Test
	public void unlockFileTest() {
		FileLockLogic lock = new FileLockLogic(FILEPATH);
		try {
			lock.lockFile();
			assertTrue(true);
		} finally {
			lock.unlockFile();
		}
	}

	@Test
	public void unlockFileTest2() {
		FileLockLogic lock = new FileLockLogic(FILEPATH);
		lock.unlockFile();
	}

}
