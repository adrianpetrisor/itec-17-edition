# ⚠️ Disclaimer  

This project is incomplete and partially non-functional due to time constraints during the 40-hour hackathon (iTEC Edition 17 — Haufe Group Full Stack Development Challenge).  

The application was developed as a proof of concept and a work-in-progress demo.  
Many planned features were left unfinished or only partially implemented.

---

# Application Monitoring Dashboard  
### Full Stack Development — Haufe Group Challenge  
#### iTEC Edition 17  

This project was developed as part of the iTEC 17 competition, Full Stack Development section, organized by Haufe Group.

The goal of the project is to build a full stack web application that provides real-time monitoring and visualization of the status of other applications and their API endpoints.

---

## ✨ Features  

### Tier 1 — Application & Endpoint Management  
- Users can register applications they want to monitor.  
- Each application can have multiple API endpoints configured.  
- Dedicated dashboard for every application showing the status of endpoints in the last 12 hours.  
- Users can report bugs at a 5-minute granularity.

---

### Tier 2 — Health Check Logic  
The application automatically checks the status of endpoints by sending HTTP requests:  
- 🟢 **Healthy** — Response status `200`.  
- 🟡 **Unstable** — Bug reported by a user.  
- 🔴 **Down** — Any other response status.

The dashboard provides a visual representation of the status evolution for each endpoint.

---

### Tier 3 — Bug Reporting System  
- Reporting a bug instantly switches the application state to *Unstable*.  
- The state returns to *Healthy* only after developer confirmation.

---

### Tier 4 — Developer Management  
- Developers authenticate to access the dashboard.  
- Developers can register as responsible for specific applications.  
- They receive notifications when a bug is reported on their applications.

---

### Tier 5 — Real-Time Monitoring  
- The dashboard updates in real-time based on the configured refresh interval:  
`1 sec`, `15 sec`, `30 sec`, `1 min`, `5 min`, etc.

---

## 💻 Technologies Used  

- `Anime.js` — Animations & Transitions  
- `jQuery` — DOM Manipulation & Event Handling  
- `Bootstrap` — Responsive UI Design  
- `Thymeleaf` — Server-Side Rendering & Templating  

---

## 🚀 Running the Project  

1. Clone the repository:  
```bash
git clone https://github.com/negatiwe/itec-edition-17.git
