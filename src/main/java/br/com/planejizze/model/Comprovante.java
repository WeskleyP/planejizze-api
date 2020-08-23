package br.com.planejizze.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true)
@Data
@Entity
@Table(name = "comprovante")
@TypeDefs(@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class))
public class Comprovante {
    @Id
    @Column(name = "uuid")
    private String uuid;
    @ManyToOne
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "comprovante_usuario_fkey"))
    private Usuario user;
    @Type(type = "jsonb")
    @Column(name = "payload", columnDefinition = "jsonb")
    private String payload;
    @Column(name = "expire")
    private Long expire;
    @Column(name = "requested_at")
    private Long requestedAt;
    @Column(name = "active")
    private Boolean active;
}
