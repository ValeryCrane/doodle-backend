package com.valerycrane.doodlebackend.entity.subtypes;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoundTypeConverter implements AttributeConverter<RoundType, String> {
    @Override
    public String convertToDatabaseColumn(RoundType roundType) {
        switch (roundType) {
            case TEXT -> {
                return "text";
            }
            case TEXT_TO_IMAGE -> {
                return "textToImage";
            }
            case IMAGE_TO_TEXT -> {
                return "imageToText";
            }
        }
        return null;
    }

    @Override
    public RoundType convertToEntityAttribute(String string) {
        switch (string) {
            case "text" -> {
                return RoundType.TEXT;
            }
            case "textToImage" -> {
                return RoundType.TEXT_TO_IMAGE;
            }
            case "imageToText" -> {
                return RoundType.IMAGE_TO_TEXT;
            }
        }
        return null;
    }
}
