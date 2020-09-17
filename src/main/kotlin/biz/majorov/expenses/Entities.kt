package biz.majorov.expenses


import java.time.LocalDate
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.NumberSerializers
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.time.format.DateTimeFormatter

//https://spring.io/guides/tutorials/spring-boot-kotlin/

@JsonIgnoreProperties(ignoreUnknown = true)
class Expense(
		@JsonProperty(value = "id")
		var id: Int? = null,
		@JsonProperty(value = "description")
		var description: String,

		@JsonProperty(value = "createdAT")
		@JsonDeserialize(using = LocalDateDeserializer::class)
		@JsonSerialize(using = LocalDateSerializer::class)

		var createdAT: LocalDate? = null,

		@JsonProperty("amount")
		//@JsonSerialize(using = NumberSerializers.DoubleSerializer::class)
		//	@JsonDeserialize(using = NumberDeserializers.DoubleDeserializer::class)
		var amount: Double,
		// @ManyToOne var author: User,

		@JsonProperty(value = "tstamp")
		@JsonDeserialize(using = LocalDateDeserializer::class)
		@JsonSerialize(using = LocalDateSerializer::class)
		var tstamp: LocalDate? = LocalDate.now()
) {
    constructor() : this(null, "", null, 0.0)

    override fun toString(): String = """[ id: ${id} | description: ${description} | amout: ${amount} | createAt: ${createdAT} ] """
}


class LocalDateDeserializer : StdDeserializer<LocalDate>(LocalDate::class.java) {

    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): LocalDate {
        return LocalDate.parse(jp.readValueAs(String::class.java))
    }

}

class LocalDateSerializer : StdSerializer<LocalDate>(LocalDate::class.java) {
    override fun serialize(value: LocalDate, gen: JsonGenerator, sp: SerializerProvider) {
        gen.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
