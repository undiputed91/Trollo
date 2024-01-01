package org.nbc.account.trollo.domain.card.converter;

import org.nbc.account.trollo.domain.card.exception.BadCardSequenceDirectionRequestException;
import org.nbc.account.trollo.global.exception.ErrorCode;
import org.springframework.core.convert.converter.Converter;

public class StringToCardSequenceDirectionConverter implements
    Converter<String, CardSequenceDirection> {

    @Override
    public CardSequenceDirection convert(final String source) {
        try {
            return CardSequenceDirection.valueOf(source.toUpperCase());
        } catch (Exception e) {
            throw new BadCardSequenceDirectionRequestException(ErrorCode.BAD_SEQUENCE_DIRECTION);
        }
    }
}
