package kr.server.productproject.domain.common;

public interface CryptoUtil {
    String encrypt(String data);
    String decrypt(String data);
}
