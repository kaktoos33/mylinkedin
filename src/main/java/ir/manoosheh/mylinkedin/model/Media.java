package ir.manoosheh.mylinkedin.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String type;

    private String filename;

    private long size;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", unique = true)
    private UserPost userPost;

    public Media(String fileName, Long size, String type) {
        this.filename = fileName;
        this.size = size;
        this.type = type;
    }
}
