//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1-b171012.0423 
//         See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
//         Any modifications to this file will be lost upon recompilation of the source schema. 
//         Generated on: 2020.04.17 at 05:00:20 PM IDT 
//


package data.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected         content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{}Owner"/&gt;
 *         &lt;element ref="{}Capacity"/&gt;
 *         &lt;element ref="{}PPK"/&gt;
 *         &lt;element ref="{}Route"/&gt;
 *         &lt;element ref="{}Scheduling"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "owner",
    "capacity",
    "ppk",
    "route",
    "scheduling"
})
@XmlRootElement(name = "TransPoolTrip")
public class TransPoolTrip {

    @XmlElement(name = "Owner", required = true)
    protected String owner;
    @XmlElement(name = "Capacity")
    protected int capacity;
    @XmlElement(name = "PPK")
    protected int ppk;
    @XmlElement(name = "Route", required = true)
    protected Route route;
    @XmlElement(name = "Scheduling", required = true)
    protected Scheduling scheduling;

    /**
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwner(String value) {
        this.owner = value;
    }

    /**
     * Gets the value of the capacity property.
     * 
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the value of the capacity property.
     * 
     */
    public void setCapacity(int value) {
        this.capacity = value;
    }

    /**
     * Gets the value of the ppk property.
     * 
     */
    public int getPPK() {
        return ppk;
    }

    /**
     * Sets the value of the ppk property.
     * 
     */
    public void setPPK(int value) {
        this.ppk = value;
    }

    /**
     * Gets the value of the route property.
     * 
     * @return
     *     possible object is
     *     {@link Route }
     *     
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Sets the value of the route property.
     * 
     * @param value
     *     allowed object is
     *     {@link Route }
     *     
     */
    public void setRoute(Route value) {
        this.route = value;
    }

    /**
     * Gets the value of the scheduling property.
     * 
     * @return
     *     possible object is
     *     {@link Scheduling }
     *     
     */
    public Scheduling getScheduling() {
        return scheduling;
    }

    /**
     * Sets the value of the scheduling property.
     * 
     * @param value
     *     allowed object is
     *     {@link Scheduling }
     *     
     */
    public void setScheduling(Scheduling value) {
        this.scheduling = value;
    }

}
