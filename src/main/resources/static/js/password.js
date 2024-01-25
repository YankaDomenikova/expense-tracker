let eyeIcon = document.getElementById('eye-icon');
let passInput = document.getElementById('password');

eyeIcon.addEventListener('click', () => {
    if(passInput.type === 'password'){
        passInput.type = 'text';
        eyeIcon.src = './icons/eye-close-2.svg'
    }else{
        passInput.type = 'password';
        eyeIcon.src = './icons/eye-open-2.svg'
    }
});