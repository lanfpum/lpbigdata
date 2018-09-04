package lxpsee.lxpsee.javaday03.udp.screenbroadcast;

import java.io.Serializable;

/**
 * 切割屏幕图片的帧单元
 * <p>
 * 努力常态化  2018/7/4 15:02 The world always makes way for the dreamer
 */
public class FrameUnit implements Serializable {
    private long frameId;       // 8字节 截屏id
    private int  frameUnitCount; // 1字节 切屏之后帧单元的个数
    private int  frameUnitNo;    // 1字节 切屏之后帧单元的编号

    private int    dataLen;        // 4字节 数据长度
    private byte[] dataBytes;

    public long getFrameId() {
        return frameId;
    }

    public void setFrameId(long frameId) {
        this.frameId = frameId;
    }

    public int getFrameUnitCount() {
        return frameUnitCount;
    }

    public void setFrameUnitCount(int frameUnitCount) {
        this.frameUnitCount = frameUnitCount;
    }

    public int getFrameUnitNo() {
        return frameUnitNo;
    }

    public void setFrameUnitNo(int frameUnitNo) {
        this.frameUnitNo = frameUnitNo;
    }

    public int getDataLen() {
        return dataLen;
    }

    public void setDataLen(int dataLen) {
        this.dataLen = dataLen;
    }

    public byte[] getDataBytes() {
        return dataBytes;
    }

    public void setDataBytes(byte[] dataBytes) {
        this.dataBytes = dataBytes;
    }
}
