package lxpsee.lxpsee.javaday01.archiver.model;

/**
 * 努力常态化  2018/6/26 8:53 The world always makes way for the dreamer
 */
public class FileBean {

    private String fileName;
    private byte[] fileContentBytes;

    public FileBean() {
    }

    public FileBean(String fileName, byte[] fileContentBytes) {
        this.fileName = fileName;
        this.fileContentBytes = fileContentBytes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileContentBytes() {
        return fileContentBytes;
    }

    public void setFileContentBytes(byte[] fileContentBytes) {
        this.fileContentBytes = fileContentBytes;
    }
}
