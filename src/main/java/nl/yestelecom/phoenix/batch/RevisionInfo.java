package nl.yestelecom.phoenix.batch;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import lombok.Data;

@Entity
@Data
@RevisionEntity(EntityRevisionListener.class)
public class RevisionInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_revision")
	@SequenceGenerator(name="seq_revision", sequenceName="seq_revision", allocationSize=1)
	@RevisionNumber
	private int id;

	@RevisionTimestamp
	private Date timestamp;

	private String username;
}