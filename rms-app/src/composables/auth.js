import { ref, watchEffect } from 'vue';

export function useSessionLogin() {
    const isAuthenticated = ref(false);
    const token = ref(null);

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

            token.value = data.token
            isAuthenticated.value = true
        } catch (error) {
            console.error('Login error:', error.message)
        }
    }

    function logout() {
        sessionStorage.removeItem('token')
        token.value = null
        isAuthenticated.value = false
    }

    if (sessionStorage.getItem('token')) {
        token.value = sessionStorage.getItem('token')
        isAuthenticated.value = true
    }
    
    return {
        isAuthenticated,
        token,
        login,
        logout
    }    
}