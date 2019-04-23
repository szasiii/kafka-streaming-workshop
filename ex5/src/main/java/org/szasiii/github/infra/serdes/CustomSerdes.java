package org.szasiii.github.infra.serdes;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.szasiii.github.infra.deserializers.ClinicDeserializer;
import org.szasiii.github.infra.deserializers.DisaseDeseralizer;
import org.szasiii.github.infra.deserializers.DoctorDeserializer;
import org.szasiii.github.infra.deserializers.MedicineDeserializer;
import org.szasiii.github.infra.deserializers.PrescriptionDeserializer;
import org.szasiii.github.infra.model.Clinic;
import org.szasiii.github.infra.model.Disease;
import org.szasiii.github.infra.model.Doctor;
import org.szasiii.github.infra.model.Medicine;
import org.szasiii.github.infra.model.Prescription;
import org.szasiii.github.infra.serializers.ClinicSerializer;
import org.szasiii.github.infra.serializers.DisaseSerializer;
import org.szasiii.github.infra.serializers.DoctorSerializer;
import org.szasiii.github.infra.serializers.MedicineSerializer;
import org.szasiii.github.infra.serializers.PrescriptionSerializer;

public class CustomSerdes {

    public static Serde<Clinic> CLINIC = Serdes.serdeFrom(new ClinicSerializer(), new ClinicDeserializer());
    public static Serde<Doctor> DOCTOR = Serdes.serdeFrom(new DoctorSerializer(), new DoctorDeserializer());
    public static Serde<Disease> DISASE = Serdes.serdeFrom(new DisaseSerializer(), new DisaseDeseralizer());
    public static Serde<Medicine> MEDICINE = Serdes.serdeFrom(new MedicineSerializer(), new MedicineDeserializer());
    public static Serde<Prescription> PRESCRIPTION = Serdes.serdeFrom(new PrescriptionSerializer(), new PrescriptionDeserializer());

}
