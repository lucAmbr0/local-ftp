import './App.css'
import useThemeColor from "./scripts/useThemeColor";
import LoginPage from './pages/LoginPage/LoginPage';
import Header from './components/Header/Header';

function App() {
  useThemeColor();

  return (
    <>
      <Header />
      <LoginPage />
    </>
  )
}

export default App
