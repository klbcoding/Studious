const greetTextDiv = document.getElementById("greet-text");
const studentDataDiv = document.getElementById("student-data");


const fetchStudentData = async () => {
    try {
        const res = await fetch("http://localhost:8080/api/studentData.json");
        const data = await res.json();
        const { name, year, major, email } = data;
        localStorage.setItem("email", email);
        greetStudent(name, year, major);
    } catch (err) {
        console.log(err);
        // Unauthenticated client. To redirect to login page.
        window.location.replace("http://localhost:8080/login") 
    }
}

fetchStudentData();


const greetStudent = (name, year, major) => {
    const date = new Date();
    const hour = date.getHours();
    if (hour >= 0 && hour <= 11) {
        greetTextDiv.textContent = "Good Morning,";
    } else if (hour >= 12 && hour <= 17) {
        greetTextDiv.textContent = "Good Afternoon,";
    } else {
        greetTextDiv.textContent = "Good Evening,";
    }
    studentDataDiv.innerHTML = `<span>${name}</span><br><span>Year ${year}, ${major}</span>`;
}




