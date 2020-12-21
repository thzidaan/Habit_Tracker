# Variable Names
Use Camel case

# Design Elements (GUI) 
Use Camel case

| Prefix | Type       | Example     |
|--------|------------|-----------|
| `btn`    | button     | `btnAccept`   |
| `tgl`    | toggle     | `tglOff`      |
| `txt`    | Text Field | `txtNotes`    |


# Acceptable abbreviations 
`Num`, `Max`, `Min`, `Avg`
# Functions
* When **naming a function**, use a **verb** followed by an **object** to describe what the function does. For example, `getClientData()`
* **Function names** will begin with a word **beginning with a lowercase letter** and each consecutive word will begin with an **uppercase letter**. 
* **Block Comments** should appear **before the function header** and should follow this outline, unless the function header fully describes its purpose:

       
        /*
         * Function Name
         * Description of the function
         * Input Type
         * Output Type
         */
    
* Functions should have only **one** `return` statement.
* **Case statements** are allowed. 
* **For Loops**
    * Use the standard `i`, `j`, `k` loop variables. If there are more than **3** nested loops, continue naming the loop variables  alphabetically from `k`. 
    * For Loops will be written like: 
        ``` java
        for(int i = 0; i <10; i++){
            //stuff
        }
        ```
        or like:
    
        ```	java
        for(Object obj: objList){
            //stuff
        }
        ```
        


# If Statements
If statements will be written as follows: 
``` java
averageNum(){
    if (bool){ //inline comment, if necessary
        // stuff
    } else {
        //stuff
    }
}
```
