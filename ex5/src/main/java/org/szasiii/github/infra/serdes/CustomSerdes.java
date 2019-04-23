package org.szasiii.github.infra.serdes;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.szasiii.github.infra.deserializers.DisaseDeseralizer;
import org.szasiii.github.infra.deserializers.DoctorDeserializer;
import org.szasiii.github.infra.deserializers.IdDeserializer;
import org.szasiii.github.infra.deserializers.PrescriptionDeserializer;
import org.szasiii.github.infra.model.Disease;
import org.szasiii.github.infra.model.Doctor;
import org.szasiii.github.infra.model.Id;
import org.szasiii.github.infra.model.Prescription;
import org.szasiii.github.infra.serializers.DisaseSerializer;
import org.szasiii.github.infra.serializers.DoctorSerializer;
import org.szasiii.github.infra.serializers.IdSerializer;
import org.szasiii.github.infra.serializers.PrescriptionSerializer;

public class CustomSerdes {

    public static Serde<Doctor> DOCTOR = Serdes.serdeFrom(new DoctorSerializer(), new DoctorDeserializer());
    public static Serde<Disease> DISASE = Serdes.serdeFrom(new DisaseSerializer(), new DisaseDeseralizer());
    public static Serde<Prescription> PRESCRIPTION = Serdes.serdeFrom(new PrescriptionSerializer(), new PrescriptionDeserializer());
    public static Serde<Id> ID = Serdes.serdeFrom(new IdSerializer(), new IdDeserializer());

}
