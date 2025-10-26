package com.b.simple.design.business.student;

public class StudentHelper {

    /* ------------------------- CONSTANTES ------------------------- */

    private static final int GRADE_LOWER_LIMIT = 50;
    private static final int GRADE_UPPER_LIMIT = 80;
    private static final int MATH_GRADE_UPPER_LIMIT = 90;
    private static final int MATH_EXTRA_THRESHOLD = 5;
    private static final int POOR_THRESHOLD = 20;
    private static final int GOOD_THRESHOLD = 80;

    /* ------------------------- ENUMS ------------------------- */

    private enum Grade {
        A, B, C
    }

    private enum QuizResult {
        YES, NO, MAYBE
    }

    /* ------------------------- 1. GRADE B VERIFICATION ------------------------- */

    /**
     * Vérifie si les notes correspondent à un grade B.
     * Grade B : 51 à 80 inclus (ou jusqu'à 90 pour les maths).
     */
    public boolean isGradeB(int marks, boolean isMaths) {
        int upperLimit = getGradeUpperLimit(isMaths);
        return marks > GRADE_LOWER_LIMIT && marks <= upperLimit;
    }

    private int getGradeUpperLimit(boolean isMaths) {
        return isMaths ? MATH_GRADE_UPPER_LIMIT : GRADE_UPPER_LIMIT;
    }

    /* ------------------------- 2. CALCUL DU GRADE ------------------------- */

    /**
     * Retourne la note finale (A, B, C) en fonction des marks et du type de matière.
     */
    public String getGrade(int marks, boolean isMaths) {
        if (isGradeA(marks, isMaths)) return Grade.A.name();
        if (isGradeBRange(marks, isMaths)) return Grade.B.name();
        return Grade.C.name();
    }

    private boolean isGradeA(int marks, boolean isMaths) {
        int threshold = isMaths
                ? MATH_GRADE_UPPER_LIMIT + MATH_EXTRA_THRESHOLD
                : GRADE_UPPER_LIMIT + MATH_EXTRA_THRESHOLD;
        return marks > threshold;
    }

    private boolean isGradeBRange(int marks, boolean isMaths) {
        int lowerLimit = GRADE_LOWER_LIMIT + (isMaths ? MATH_EXTRA_THRESHOLD : 0);
        int upperLimit = MATH_GRADE_UPPER_LIMIT;
        return marks > lowerLimit && marks <= upperLimit;
    }

    /* ------------------------- 3. QUALIFICATION AU QUIZ ------------------------- */

    /**
     * Détermine si deux étudiants peuvent participer au quiz :
     * - YES : si l’un des deux a >= 80 (ou 85 pour maths)
     * - NO : si l’un des deux a <= 20 (ou 25 pour maths)
     * - MAYBE : sinon
     */
    public String willQualifyForQuiz(int marks1, int marks2, boolean isMaths) {
        if (hasFailedToQualify(marks1, marks2, isMaths)) return QuizResult.NO.name();
        if (hasQualified(marks1, marks2, isMaths)) return QuizResult.YES.name();
        return QuizResult.MAYBE.name();
    }

    private boolean hasFailedToQualify(int marks1, int marks2, boolean isMaths) {
        int poorLimit = POOR_THRESHOLD + (isMaths ? MATH_EXTRA_THRESHOLD : 0);
        return marks1 <= poorLimit || marks2 <= poorLimit;
    }

    private boolean hasQualified(int marks1, int marks2, boolean isMaths) {
        int goodLimit = GOOD_THRESHOLD + (isMaths ? MATH_EXTRA_THRESHOLD : 0);
        return marks1 >= goodLimit || marks2 >= goodLimit;
    }
}
