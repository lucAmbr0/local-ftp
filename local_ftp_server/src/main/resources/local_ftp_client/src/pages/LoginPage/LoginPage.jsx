import { useData } from "../../scripts/useData";
import styles from "./LoginPage.module.css";
import LoginForm from "../../components/LoginForm/LoginForm";

function LoginPage() {
  const [data, setData] = useData();

  const element = (
    <div className={styles.container}>
      <LoginForm />
    </div>
  );

  return element;
}

export default LoginPage;
