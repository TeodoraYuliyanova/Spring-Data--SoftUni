package entities;


import javax.persistence.*;

@Entity
@Table(name = "magic_wand")
public class MagicWand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String creator;

    @Column
    private Long size;

    public MagicWand() {
    }


    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    protected void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getSize() {
        return size;
    }

    protected void setSize(Long size) {
        this.size = size;
    }
}
