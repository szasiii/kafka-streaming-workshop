package org.szasiii.github.infra.serdes;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.szasiii.github.infra.deserializers.ClinicDeserializer;
import org.szasiii.github.infra.deserializers.DoctorDeserializer;
import org.szasiii.github.infra.model.Clinic;
import org.szasiii.github.infra.model.Doctor;
import org.szasiii.github.infra.serializers.ClinicSerializer;
import org.szasiii.github.infra.serializers.DoctorSerializer;

public class CustomSerdes {

    public static Serde<Clinic> CLINIC = Serdes.serdeFrom(new ClinicSerializer(), new ClinicDeserializer());
    public static Serde<Doctor> DOCTOR = Serdes.serdeFrom(new DoctorSerializer(), new DoctorDeserializer());

}
