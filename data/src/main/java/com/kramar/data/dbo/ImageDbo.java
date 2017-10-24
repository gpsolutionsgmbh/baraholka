package com.kramar.data.dbo;

import com.kramar.data.type.ImageType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImageDbo extends AbstractEntity {

    private static final long serialVersionUID = -6606909457821544151L;

    @Lob
    @Lazy
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "content")
    private byte[] content;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "image_type")
    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    public ImageDbo(String mimeType, byte[] content) {
        this.mimeType = mimeType;
        this.content = content;
    }
}
