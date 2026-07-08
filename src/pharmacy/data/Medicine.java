package pharmacy.data;

public class Medicine {
    private int medicineID;
    private String medicineName;
    private String genericName;
    private String category;
    private String dosageForm;
    private String unit;
    private int reorderLevel;

    public Medicine(int mid, String mName, String gName, String cat, String dForm, String u, int rLevel) {
        medicineID = mid;
        medicineName = mName;
        genericName = gName;
        category = cat;
        dosageForm = dForm;
        unit = u;
        reorderLevel = rLevel;

    }

    public int getMedicineID() {
        return medicineID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getGenericName() {
        return genericName;
    }

    public String getCategory() {
        return category;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public String getUnit() {
        return unit;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setMedicineName(String mNameS) {
        medicineName = mNameS;
    }

    public void setGenericName(String gNameS) {
        genericName = gNameS;
    }

    public void setCategory(String catS) {
        category = catS;
    }

    public void setDosageForm(String dFormS) {
        dosageForm = dFormS;
    }

    public void setUnit(String uS) {
        unit = uS;
    }

    public void setReorderLevel(int rLevelS) {
        reorderLevel = rLevelS;
    }

}
