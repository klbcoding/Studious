// Check if client is logged in
if (!localStorage.getItem("email")) {
    // If not logged in, to redirect to login page.
    window.location.replace("http://localhost:8080/login");
}

const searchMode = document.getElementById("search-mode");
const searchContainer = document.getElementById("search-container");
const courseContainer = document.getElementById("course-container");
const nameSearchMethods = document.getElementById("name-methods");
const table = document.querySelector("table");
const startsWithMethod = document.getElementById("starts-with");


const fetchCourseData = async () => {
    try {
        const res = await fetch("http://localhost:8080/api/courses.json");
        const data = await res.json();
        table.style.display = (data.length === 0) ? "none" : "block";
        displayCourses(data);
    } catch (err) {
        console.log(err);
    }
}

fetchCourseData();


const colorCode = (difficulty) => {
    switch (difficulty) {
        case "Easy":
            return "#b6ffac";
        case "Medium":
            return "#f9ffac";
        case "Hard":
            return "#ffacac";
    }
}


const displayCourses = (data) => {
    courseContainer.innerHTML = data.map((course) => {
        const {
            code,
            subject,
            title,
            difficulty,
            commitment,
            credits,
            price,
            description
        } = course;

        // colour-coding based on difficulty
        let color = colorCode(difficulty);
        
        return `
        <tr style="background-color: ${color};">
            <td>${code}</td>
            <td>
                ${title}<br>[${subject}]
            </td>
            <td>${commitment}</td>
            <td>${credits}</td>
            <td>$${price}</td>
            <td>${description}</td>
        </tr>
        `;
    }).join("");
}


const displaySearchContainer = (mode) => {
    switch (mode) {
        case "all":
            searchContainer.style.display = "none";
            startsWithMethod.required = false;
            break;
        case "course-code":
            searchContainer.style.display = "block";
            nameSearchMethods.style.display = "none";
            startsWithMethod.required = false;
            break;
        case "course-name":
            searchContainer.style.display = "block";
            nameSearchMethods.style.display = "block";
            startsWithMethod.required = true;
            break;
    }
}

searchMode.addEventListener("change", (e) => {
    // display the search interface after the student selects an option from the dropdown
    displaySearchContainer(e.target.value);
});


