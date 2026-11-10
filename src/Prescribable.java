/**
 * The capability of prescribing medication to a patient.
 *
 * <p>Not every health professional is allowed to prescribe drugs, and the
 * ones who can are not all of the same type: a general practitioner, a
 * specialist and a surgeon can prescribe, while a nurse cannot. That is a
 * capability rather than a kind of professional, so it does not belong in the
 * {@link HealthProfessional} class hierarchy - a class can only extend one
 * parent, but it can implement as many interfaces as it needs.</p>
 *
 * <p>The class that uses this capability (the demonstration in
 * {@code AssignmentOne}) can be written against the interface alone, without
 * knowing which concrete type it is dealing with.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.0
 */
public interface Prescribable {

    /**
     * Prescribes a medication to a patient.
     *
     * @param medication  the name of the medication
     * @param patientName the name of the patient receiving it
     * @return a readable message describing what was prescribed and by whom
     */
    String prescribe(String medication, String patientName);
}
