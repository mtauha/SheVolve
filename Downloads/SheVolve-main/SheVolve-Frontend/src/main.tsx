import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.tsx'
// import SignUp from './pages/SignUp'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    < App/>
  </StrictMode>,
)
