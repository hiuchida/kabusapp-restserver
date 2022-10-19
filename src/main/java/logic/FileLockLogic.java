package logic;

import java.nio.channels.OverlappingFileLockException;

import util.FileLockUtil;

/**
 * ファイルロックを管理するクラス。
 */
public class FileLockLogic {

	/**
	 * ロックファイルパス名。
	 */
	private String lockFilename;

	/**
	 * ファイルロック情報。
	 */
	private FileLockUtil.LockInfo lock = new FileLockUtil.LockInfo();

	/**
	 * コンストラクタ。
	 * 
	 * @param lockFilename ロックファイルパス名。
	 */
	public FileLockLogic(String lockFilename) {
		this.lockFilename = lockFilename;
	}

	/**
	 * ファイルロックする。
	 * 
	 * @throws IllegalStateException 既にロック済の場合。
	 * @throws OverlappingFileLockException 同じJavaVMで同じファイルをロック済の場合。
	 */
	public void lockFile() {
		boolean bFirst = true;
		while (true) {
			if (FileLockUtil.lock(lock, false, lockFilename)) {
				break;
			}
			if (bFirst) {
				System.out.println("Waiting for other processes to release file.");
				bFirst = false;
			}
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * ファイルロックを解放する。
	 */
	public void unlockFile() {
		FileLockUtil.unlock(lock);
	}

	@Override
	public String toString() {
		return lockFilename;
//		return "FileLockLogic [lockFilename=" + lockFilename + ", lock=" + lock + "]";
	}

}
