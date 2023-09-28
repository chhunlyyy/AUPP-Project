import { ref, reactive } from 'vue';

export function useSessionLogin() {
    const isAuthenticated = ref(false);
    const token = ref(null);
    const roleId = ref(null);
    const userId = ref(null);

    async function login(email, password) {
        try {
            const response = await fetch('http://localhost:8080/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email, password })
            });

            if (!response.ok) {
                throw new Error('Login failed')
            }

            const data = await response.json()

            sessionStorage.setItem('token', data.token)
            sessionStorage.setItem('userId', data.data.id)
            sessionStorage.setItem('roleId', data.data.role_id)

            token.value = data.token;
            roleId.value = data.data.role_id
            userId.value = data.data.id
            isAuthenticated.value = true;
            
        } catch (error) {
            console.error('Login error:', error.message)
        }
    }

    function logout() {
        sessionStorage.removeItem('token');
        sessionStorage.removeItem('roleId');
        sessionStorage.removeItem('userId');
        token.value = null;
        roleId.value = null;
        userId.value = null;
        isAuthenticated.value = false;
    }

    if (sessionStorage.getItem('token') && ["1","2","3"].includes(sessionStorage.getItem('roleId'))) {
        token.value = sessionStorage.getItem('token')
        roleId.value = parseInt(sessionStorage.getItem('roleId'))
        userId.value = parseInt(sessionStorage.getItem('userId'))
        isAuthenticated.value = true
    }
    
    return {
        isAuthenticated,
        token,
        roleId,
        userId,
        login,
        logout
    }    
}