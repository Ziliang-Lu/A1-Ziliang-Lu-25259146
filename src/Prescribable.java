/**
 * The capability of prescribing medication to a patient.
 *
 * <p><b>Why this is an interface and not another subclass.</b> Not every health
 * professional is allowed to prescribe, and the ones who can are not all of the
 * same type: a general practitioner, a specialist and a surgeon can prescribe,
 * while a nurse cannot. Prescribing is therefore a capability that cuts across
 * the class hierarchy rather than a kind of professional, and it cannot be
 * expressed by inheritance because a class can only extend one parent. An
 * interface lets one class promise several capabilities while still extending
 * exactly one class, which is the point of interfaces in Module 3.</p>
 *
 * <p><b>Who implements it.</b> {@code GeneralPractitioner},
 * {@code Specialist} and {@code Surgeon} declare {@code implements
 * Prescribable}; {@code Nurse} deliberately does not. That difference is what
 * lets the demonstration ask a list of health professionals "can this one
 * prescribe", and it is checked by the compiler rather than by a comment.</p>
 *
 * <p><b>What the caller gains.</b> Code that only needs the capability can be
 * written against this interface alone and never mention a concrete type, so
 * adding a fourth doctor type later would not require that code to change.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.2
 */
public interface Prescribable {

    /**
     * Prescribes a medication to a patient.
     *
     * <p>Every implementation returns a readable sentence instead of printing
     * it, so the caller decides where the text appears. Keeping the message as
     * a return value also keeps the method testable and reusable, because
     * nothing is written to the console from inside the domain classes.</p>
     *
     * @param medication the name of the medication, for example "paracetamol"
     * @param patientName the name of the patient receiving it
     * @return a message describing what was prescribed and by whom
     */
    String prescribe(String medication, String patientName);
}
