package org.szasiii.github.kstreams;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.szasiii.github.infra.model.Clinic;
import org.szasiii.github.infra.model.Doctor;
import org.szasiii.github.infra.serializers.ClinicSerializer;
import org.szasiii.github.infra.serializers.DoctorSerializer;

import static org.szasiii.kstreams.Utils.createCustomProducer;

public class Ex4Producer {
    public static void main(String[] args) throws Exception {

        Producer<Long, Doctor> doctorProducer = createCustomProducer(LongSerializer.class.getName(), DoctorSerializer.class.getName());
        Producer<Long, Clinic> clinicProducer = createCustomProducer(LongSerializer.class.getName(), ClinicSerializer.class.getName());


        clinicProducer.send(new ProducerRecord<>("ex4-clinics", 1000L, new Clinic(1000L, "clinic", "REG")));

        doctorProducer.send(new ProducerRecord<>("ex4-doctors", 1000L, new Doctor(1000L, 1000L, "bambi", "CRD")));
        doctorProducer.send(new ProducerRecord<>("ex4-doctors", 1001L, new Doctor(1001L, 1000L, "bambi1", "CRD1")));
        doctorProducer.send(new ProducerRecord<>("ex4-doctors", 1002L, new Doctor(1002L, 1000L, "bambi2", "CRD2")));
        doctorProducer.send(new ProducerRecord<>("ex4-doctors", 1003L, new Doctor(1003L, 1001L, "bambi3", "CRD3")));
        doctorProducer.send(new ProducerRecord<>("ex4-doctors", 1004L, new Doctor(1004L, 1001L, "bambi4", "CRD4")));
        doctorProducer.send(new ProducerRecord<>("ex4-doctors", 1005L, new Doctor(1005L, 1001L, "bambi5", "CRD5")));

        clinicProducer.send(new ProducerRecord<>("ex4-clinics", 1001L, new Clinic(1001L, "clinic1", "REG1")));

        doctorProducer.send(new ProducerRecord<>("ex4-doctors", 1006L, new Doctor(1006L, 1001L, "bambi6", "CRD6")));
        doctorProducer.send(new ProducerRecord<>("ex4-doctors", 1007L, new Doctor(1007L, 1002L, "bambi7", "CRD7")));
        doctorProducer.send(new ProducerRecord<>("ex4-doctors", 1008L, new Doctor(1008L, 1002L, "bambi8", "CRD8")));
        doctorProducer.send(new ProducerRecord<>("ex4-doctors", 1009L, new Doctor(1009L, 1002L, "bambi9", "CRD9")));
        doctorProducer.send(new ProducerRecord<>("ex4-doctors", 1010L, new Doctor(1010L, 1003L, "bambi10", "CRD10")));
        doctorProducer.send(new ProducerRecord<>("ex4-doctors", 1011L, new Doctor(1011L, 1003L, "bambi11", "CRD11")));


        clinicProducer.send(new ProducerRecord<>("ex4-clinics", 1002L, new Clinic(1002L, "clinic2", "REG2")));
    }


}
