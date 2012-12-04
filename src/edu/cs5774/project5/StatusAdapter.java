package edu.cs5774.project5;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.cs5774.project5.TaskBug.Status;

/**
 * JAXB XML parsing adapter needed for converting string representations of the Status enumeration
 * from the RSS feed since the enum and XML values cannot match since XML contains whitespace.
 *
 */
public class StatusAdapter extends XmlAdapter<String, Status> {

   public String marshal(Status status) {
      return status.toString();
   }

   public Status unmarshal(String val) {
      return Status.fromValue(val);
   }
}
