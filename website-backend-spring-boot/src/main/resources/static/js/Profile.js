const currentUrl = window.location.href;
const whoAmIUrl = new URL(currentUrl.replace("profile", "api/user/whoami"))

/**
 * Fetches the user from the whoami endpoint depending on which user is logged in.
 * @returns {Promise<any>}
 */
async function fetchUser(){
    try {
        const response = await fetch(whoAmIUrl.toString());

        if (!response.ok) {
            throw new Error(`failed to fetch users: ${response.status}`)
        }

        return response.json();
    } catch(e){
        console.log(e)
    }
}

/**
 * Adds the user details to the profile page.
 */

function showUserDetails(){

    fetchUser().then(user => {


        console.log(user.name);
        document.querySelector("#nameValue").innerHTML = user.name;

        console.log(user.username);
        document.querySelector("#usernameValue").innerHTML = user.username;



        console.log(user.roles[0].name)

        const roles = user.roles;

        for (let i = 0; i < roles.length; i++) {

            if (roles[i].name === "ROLE_MANAGER" || roles[i].name === "ROLE_ADMIN") {
                document.getElementById("ManagementLink").style.display = "flex";
            }

        }

    }).catch(e => {
        console.log(e);
    });


}
/**
 * We wait for the window to load before it shows the user details.
 */
window.onload = function () {

    showUserDetails();

}






