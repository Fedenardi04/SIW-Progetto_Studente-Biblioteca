package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Recensione {

@Id	
@GeneratedValue(strategy = GenerationType.AUTO)	
private Long id;
private String text;
private LocalDate creationDate;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public LocalDate getCreationDate() {
	return creationDate;
}
public void setCreationDate(LocalDate creationDate) {
	this.creationDate = creationDate;
}
@Override
public int hashCode() {
	return Objects.hash(id);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Recensione other = (Recensione) obj;
	return Objects.equals(id, other.id);
}


}
