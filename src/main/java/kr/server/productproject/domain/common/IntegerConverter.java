package kr.server.productproject.domain.common;

import jakarta.persistence.Converter;

@Converter
public class IntegerConverter extends GenericConverter<Integer> {

    public IntegerConverter(CryptoUtil cryptoUtil) {
        super(cryptoUtil);
    }

    @Override
    protected String convertToString(Integer attribute) {
        return attribute.toString();
    }

    @Override
    protected Integer convertFromString(String dbData) {
        return Integer.valueOf(dbData);
    }
}
