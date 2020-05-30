package scripts.lab1

if ("#IIN#" == "" && !("#FIRST_NAME#" != "" && "#LAST_NAME#" != "" && "#PATRONYMIC#" != "")){
    throw new Exception("Недостаточно данных для дальнейшей обработки")
} else {
    return "Успешно"
}