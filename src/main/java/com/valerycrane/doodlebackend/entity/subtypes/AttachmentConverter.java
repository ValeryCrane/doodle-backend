package com.valerycrane.doodlebackend.entity.subtypes;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AttachmentConverter implements AttributeConverter<Attachment, String> {
    @Override
    public String convertToDatabaseColumn(Attachment attachment) {
        switch (attachment.type()) {
            case TEXT -> {
                return "text:" + (attachment.payload() == null ? "" : attachment.payload());
            }
            case IMAGE -> {
                return "image:" + (attachment.payload() == null ? "" : attachment.payload());
            }
        }
        return null;
    }

    @Override
    public Attachment convertToEntityAttribute(String string) {
        if (string.startsWith("text:")) {
            String payload = string.substring("text:".length());
            return new Attachment(AttachmentType.TEXT, payload.equals("") ? null : payload);
        } else if (string.startsWith("image:")) {
            String payload = string.substring("image:".length());
            return new Attachment(AttachmentType.IMAGE, payload.equals("") ? null : payload);
        } else {
            return null;
        }
    }
}

