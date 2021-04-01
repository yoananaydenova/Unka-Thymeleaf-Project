const usersList = document.getElementById('usersList')
const searchBar = document.getElementById('searchInput')

const allUsers = [];
fetch('http://localhost:8080/users/api')
    .then(response => response.json())
    .then(data => {
        for (let user of data) {
            allUsers.push(user);
        }
    });


searchBar.addEventListener('keyup', (e) => {
    const searchingCharacters = searchBar.value.toLowerCase();
    let filteredUsers = allUsers.filter(user => {
        return user.username.toLowerCase().includes(searchingCharacters)
            || user.fullName.toLowerCase().includes(searchingCharacters);
    });
    console.log(filteredUsers)
    displayUsers(filteredUsers);
});

const displayUsers = (users) => {
    usersList.innerHTML = users
        .map((us) => {

            return ` 
                <div class="card w-75 box-shadow">
                <div class="card-body d-flex justify-content-between">
                    <div class="text-left">
                        <h6 class="card-text">ПОТРЕБИТЕЛСКО ИМЕ: ${us.username}</h6>
                        <h6 class="card-text border-bottom ">ИМЕ И ФАМИЛИЯ: ${us.fullName}</h6>
                     <h6>РОЛИ</h6>
                      <ul>
                    <li>
                        <div class="form-check">                   
                              <input type="checkbox" id="studentRole" disabled ${us.roleId.includes(1) ? 'checked' : ''} value="1">
                               <label for="studentRole">Студент</label><br>
                               <input type="checkbox" id="teacherRole" disabled name="teacherRole" ${us.roleId.includes(2) ? 'checked' : ''} value="2">
                               <label for="teacherRole">Учител</label><br>
                               <input type="checkbox" id="adminRole" disabled name="adminRole" ${us.roleId.includes(3) ? 'checked' : ''} value="3">
                               <label for="adminRole">Администратор</label><br>               
                        </div>
                    </li>
                </ul>
                     
                                      
                  </div>
                    <div>
                        <div class="btn-group">
                            <a href="/profile/edit/${us.id}"  class="btn btn-primary">Редактирай</a>
                        </div>
                    </div>
                </div>
          
            </div>`
        })
        .join('');

    function changeRole() {


    }

}