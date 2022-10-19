package util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.channels.OverlappingFileLockException;

import org.junit.Test;

public class FileLockUtilTest {
	private static final String FILEPATH = "FileLockUtilTest.lock";

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
