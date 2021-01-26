package br.com.planejizze.utils.serializer;

import br.com.planejizze.model.Planejamento;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class PlanejamentoSerializer extends StdSerializer<Planejamento> {
    private static final long serialVersionUID = 8745536738577069239L;

    public PlanejamentoSerializer() {
        this(null);
    }

    protected PlanejamentoSerializer(Class<Planejamento> t) {
        super(t);
    }

    @Override
    public void serialize(Planejamento value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeEndObject();
    }
}