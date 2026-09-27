package internship.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "auth")
@XmlAccessorType(XmlAccessType.FIELD)
public record AuthRequest(Object username, Object password) {
}
