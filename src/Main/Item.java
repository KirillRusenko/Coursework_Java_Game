package Main;

public class Item {
    private String name;
    private String description;
    private int weight;
    private int value;
    private int level;

    public Item(String name, String description, int weight, int value, int level) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.value = value;
        this.level = level;
    }

    public Object[] getItemCharacteristics() {
        return new Object[] {
                this.name,
                this.description,
                this.weight,
                this.value,
                this.level
        };
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
