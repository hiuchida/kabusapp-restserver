package util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.channels.OverlappingFileLockException;

import org.junit.jupiter.api.Test;

public class FileLockUtilTest {
	private static final String FILEPATH = "FileLockUtilTest.lock";
	private static final String FILEPATH2 = "FileLockUtilTest2.lock";

	@Test
	public void lockFalseTest() {
		FileLockUtil.LockInfo lock = new FileLockUtil.LockInfo();
		boolean bShared = false;
		String filepath = FILEPATH;
		try {
			boolean a1 = FileLockUtil.lock(lock, bShared, filepath);
			assertTrue(a1);
			assertNotNull(lock.fc);
			assertNotNull(lock.fl);
		} finally {
			FileLockUtil.unlock(lock);
		}
	}

	@Test
	public void lockFalseTest2() {
		FileLockUtil.LockInfo lock = new FileLockUtil.LockInfo();
		boolean bShared = false;
		String filepath1 = FILEPATH;
		String filepath2 = FILEPATH2;
		try {
			boolean a1 = FileLockUtil.lock(lock, bShared, filepath1);
			assertTrue(a1);
			assertNotNull(lock.fc);
			assertNotNull(lock.fl);
			try {
				FileLockUtil.lock(lock, bShared, filepath2);
				fail("must throw IllegalStateException.");
			} catch (IllegalStateException e) {
//				e.printStackTrace();
				assertNotNull(lock.fc);
				assertNotNull(lock.fl);
			}
		} finally {
			FileLockUtil.unlock(lock);
		}
	}

	@Test
	public void lockFalseTest3() {
		FileLockUtil.LockInfo lock1 = new FileLockUtil.LockInfo();
		FileLockUtil.LockInfo lock2 = new FileLockUtil.LockInfo();
		boolean bShared = false;
		String filepath = FILEPATH;
		try {
			boolean a1 = FileLockUtil.lock(lock1, bShared, filepath);
			assertTrue(a1);
			assertNotNull(lock1.fc);
			assertNotNull(lock1.fl);
			try {
				FileLockUtil.lock(lock2, bShared, filepath);
				fail("must throw OverlappingFileLockException.");
			} catch (OverlappingFileLockException e) {
//				e.printStackTrace();
				assertNull(lock2.fc);
				assertNull(lock2.fl);
			}
		} finally {
			FileLockUtil.unlock(lock1);
		}
	}

	@Test
	public void lockTrueTest() {
		FileLockUtil.LockInfo lock = new FileLockUtil.LockInfo();
		boolean bShared = true;
		String filepath = FILEPATH;
		try {
			boolean a1 = FileLockUtil.lock(lock, bShared, filepath);
			assertTrue(a1);
			assertNotNull(lock.fc);
			assertNotNull(lock.fl);
		} finally {
			FileLockUtil.unlock(lock);
		}
	}

	@Test
	public void lockTrueTest2() {
		FileLockUtil.LockInfo lock = new FileLockUtil.LockInfo();
		boolean bShared = true;
		String filepath1 = FILEPATH;
		String filepath2 = FILEPATH2;
		try {
			boolean a1 = FileLockUtil.lock(lock, bShared, filepath1);
			assertTrue(a1);
			assertNotNull(lock.fc);
			assertNotNull(lock.fl);
			try {
				FileLockUtil.lock(lock, bShared, filepath2);
				fail("must throw IllegalStateException.");
			} catch (IllegalStateException e) {
//				e.printStackTrace();
				assertNotNull(lock.fc);
				assertNotNull(lock.fl);
			}
		} finally {
			FileLockUtil.unlock(lock);
		}
	}

	@Test
	public void lockTrueTest3() {
		FileLockUtil.LockInfo lock1 = new FileLockUtil.LockInfo();
		FileLockUtil.LockInfo lock2 = new FileLockUtil.LockInfo();
		boolean bShared = true;
		String filepath = FILEPATH;
		try {
			boolean a1 = FileLockUtil.lock(lock1, bShared, filepath);
			assertTrue(a1);
			assertNotNull(lock1.fc);
			assertNotNull(lock1.fl);
			try {
				FileLockUtil.lock(lock2, bShared, filepath);
				fail("must throw OverlappingFileLockException.");
			} catch (OverlappingFileLockException e) {
//				e.printStackTrace();
				assertNull(lock2.fc);
				assertNull(lock2.fl);
			}
		} finally {
			FileLockUtil.unlock(lock1);
		}
	}

	@Test
	public void unlockTest() {
		FileLockUtil.LockInfo lock = new FileLockUtil.LockInfo();
		boolean bShared = false;
		String filepath = FILEPATH;
		try {
			boolean a1 = FileLockUtil.lock(lock, bShared, filepath);
			assertTrue(a1);
			assertNotNull(lock.fc);
			assertNotNull(lock.fl);
		} finally {
			FileLockUtil.unlock(lock);
			assertNull(lock.fc);
			assertNull(lock.fl);
		}
	}

	@Test
	public void unlockTest2() {
		FileLockUtil.LockInfo lock = new FileLockUtil.LockInfo();
		FileLockUtil.unlock(lock);
		assertNull(lock.fc);
		assertNull(lock.fl);
	}

}
