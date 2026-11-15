/**
 * Part 2: the capability of prescribing medication.
 *
 * Methods: prescribe().
 *
 * It is an interface because prescribing is something a professional can do,
 * not a kind of professional: the three doctor types implement it and the nurse
 * does not.
 */
public interface Prescribable {

    /**
     * Prescribes a medication to a patient.
     *
     * @param medication the name of the medication
     * @param patientName the name of the patient
     * @return the sentence that describes the prescription
     */
    String prescribe(String medication, String patientName);
}
