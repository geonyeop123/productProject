package kr.server.productproject.domain.common;

import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public abstract class GenericConverter<T> implements AttributeConverter<T, String> {

    private final CryptoUtil cryptoUtil;

    protected abstract String convertToString(T attribute);
    protected abstract T convertFromString(String dbData);

    @Override
    public String convertToDatabaseColumn(T data) {
        return data == null ? null : cryptoUtil.encrypt(convertToString(data));
    }

    @Override
    public T convertToEntityAttribute(String data) {
        return data == null ? null : convertFromString(cryptoUtil.decrypt(data));
    }
}
