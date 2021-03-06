const currentUrl = window.location.href;
const whoAmIUrl = new URL(currentUrl.replace("order-history", "api/user/whoami"))

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


function showUserDetails(){

    fetchUser().then(user => {



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


window.onload = function () {

    showUserDetails();

}