const toggleButton = document.getElementById("theme-toggle");
const body = document.body;
const darkModeMediaQuery = window.matchMedia("(prefers-color-scheme: dark)");
const themeIcon = document.createElement("img"); // Create an image element
themeIcon.classList.add("theme-icon"); // Add a class for styling if needed
toggleButton.appendChild(themeIcon); // Append it to the button

function updateIcon(theme) {
  themeIcon.src = theme === "dark" ?"./icons/sun.svg" : "./icons/moon.svg" ;
  themeIcon.alt = theme === "dark" ? "Dark Mode" : "Light Mode";
}

function applyTheme(theme) {
  if (theme === "dark") {
    body.classList.add("dark-theme");
    localStorage.setItem("theme", "dark");
  } else {
    body.classList.remove("dark-theme");
    localStorage.setItem("theme", "light");
  }
  updateIcon(theme); // Update the icon when applying the theme
}

const savedTheme = localStorage.getItem("theme");
if (savedTheme) {
  applyTheme(savedTheme);
} else {
  applyTheme(darkModeMediaQuery.matches ? "dark" : "light");
}

darkModeMediaQuery.addEventListener("change", (event) => {
  if (!localStorage.getItem("theme")) {
    applyTheme(event.matches ? "dark" : "light");
  }
});

toggleButton.addEventListener("click", () => {
  const newTheme = body.classList.contains("dark-theme") ? "light" : "dark";
  applyTheme(newTheme);
});
